package in.credable.automation.testcases.user;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.config.EnvironmentConfig;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.header.HeaderName;
import in.credable.automation.service.user.UserService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.user.*;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.RandomDataGenerator;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class UserManagementTest extends BaseTest {
    private final List<String> createdUserIds = new ArrayList<>();
    private final List<UserVO> bulkUsersUploadRequest = new ArrayList<>();
    private String userId;
    private String username;
    private UserVO createUserRequestVO;

    @Test(description = "Test #124 - Verify if Create user API is working correctly for file when triggered from postman")
    public void verifyCreatingUser() {
        createUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUserRequestVO.setUsername(createUserRequestVO.getEmailId());
        createUserRequestVO.setRoles(List.of(UserRoleEnum.ADMIN.getUserRole(), UserRoleEnum.CLIENT_ADMIN.getUserRole()));
        ResponseWO<CreateUserResponseVO> createdUserResponseVO = UserService.createUser(createUserRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(createdUserResponseVO)
                .hasSameStatus(200)
                .hasSameCode("USR001")
                .hasSameMessage("User Registered successfully")
                .dataIsNotNull()
                .timestampIsNotNull();

        CreateUserResponseVO createdUser = createdUserResponseVO.getData();
        this.username = createdUser.getUserName();
        FrameworkAssertions.assertThat(createdUser)
                .userIdIsNotNull()
                .hasSameUserName(username);
        this.userId = createdUser.getUserId();
        this.createdUserIds.add(this.userId);
        this.createUserRequestVO.setUserId(this.userId);
    }

    @Test(description = "Test #119 - Verify if Fetch user API is working correctly when triggered from postman"
            , dependsOnMethods = "verifyCreatingUser"
            , priority = 1)
    public void verifyFetchingUserDetails() {
        ResponseWO<UserVO> userResponseVO = UserService.getUserByUsername(this.username)
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(userResponseVO)
                .hasSameStatus(200)
                .hasSameCode("USR002")
                .hasSameMessage("User Details fetched")
                .timestampIsNotNull();

        UserVO userDetailsByUsername = userResponseVO.getData();

        Assertions.assertThat(userDetailsByUsername)
                .as(() -> "User details by username are not matching with create user request data")
                .isEqualTo(this.createUserRequestVO);

        ResponseWO<UserVO> userResponseByUserIdVO = UserService.getUserById(this.userId).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(userResponseByUserIdVO)
                .hasSameStatus(200)
                .hasSameCode("USR002")
                .hasSameMessage("User Details fetched")
                .timestampIsNotNull();

        UserVO userDetailsByUserId = userResponseVO.getData();

        Assertions.assertThat(userDetailsByUserId)
                .as(() -> "User details by user id are not matching with create user request data")
                .isEqualTo(this.createUserRequestVO);
    }

    @Test(description = "Test #120 - Verify if Fetch user API is not working correctly when triggered with invalid user name"
            , priority = 2)
    public void verifyFetchingUserDetailsForNonExistingUsername() {
        UserVO createdUser = TestHelper.createUser();
        TestHelper.deleteUser(createdUser.getUserId());
        ResponseWO<UserVO> userResponseVO = UserService.getUserByUsername(createdUser.getUsername()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(userResponseVO)
                .hasSameStatus(400)
                .hasSameCode("IAM003")
                .hasSameMessage("User not found")
                .timestampIsNotNull()
                .dataIsNull();
    }

    @Test(description = "Test #125 - Verify new user can not be created with existing users details"
            , dependsOnMethods = "verifyCreatingUser"
            , priority = 3)
    public void verifyCreatingUserWithExistingUserDetails() {
        UserVO duplicateUserRequestVO = DataProviderUtil.clone(this.createUserRequestVO);
        duplicateUserRequestVO.setUsername(this.createUserRequestVO.getUsername());
        duplicateUserRequestVO.setRoles(List.of(UserRoleEnum.ADMIN.getUserRole(), UserRoleEnum.CLIENT_ADMIN.getUserRole()));

        ResponseWO<CreateUserResponseVO> duplicateUserResponseVO = UserService.createUser(duplicateUserRequestVO)
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(duplicateUserResponseVO)
                .hasSameStatus(400)
                .hasSameCode("IAM007")
                .hasSameMessage("Duplicate Username or Email Id or Mobile Number")
                .dataIsNull()
                .timestampIsNotNull();
    }

    @Test(description = "Test #126 - Verify new user can not be created with incorrect details of client, client-secret and org id"
            , dataProvider = "authHeadersProvider"
            , priority = 4)
    public void verifyCreatingUserWithIncorrectHeaders(Map<String, String> authHeaders) {
        UserVO createUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUserRequestVO.setUsername(createUserRequestVO.getEmailId());
        createUserRequestVO.setRoles(List.of(UserRoleEnum.ADMIN.getUserRole(), UserRoleEnum.CLIENT_ADMIN.getUserRole()));

        ResponseWO<Void> responseVO = UserService.createUser(createUserRequestVO,
                        authHeaders,
                        StatusCode.UNAUTHORIZED)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(401)
                .hasSameCode("AUTH001")
                .hasSameMessage("Client is not authorized to use the Resource")
                .dataIsNull()
                .timestampIsNotNull();
    }

    @Test(description = "Test #127 - Verify if we are unable to create user without username"
            , priority = 5)
    public void verifyCreatingUserWithoutUsername() {
        UserVO createUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUserRequestVO.setRoles(List.of(UserRoleEnum.ADMIN.getUserRole(), UserRoleEnum.CLIENT_ADMIN.getUserRole()));

        ResponseWO<Void> responseVO = UserService.createUser(createUserRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(400)
                .hasSameCode("IAM008")
                .hasSameMessage("Username is required")
                .dataIsNull()
                .timestampIsNotNull();
    }

    @Test(description = "Test #128 - Verify we are unable to create user with any mandatory field missing"
            , priority = 6)
    public void verifyCreatingUserWithoutMandatoryFields() {
        UserVO createUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUserRequestVO.setUsername(createUserRequestVO.getEmailId());
        createUserRequestVO.setEmailId(null);
        createUserRequestVO.setMobileNumber(null);

        ResponseWO<Void> responseVO = UserService.createUser(createUserRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(400)
                .hasSameCode("IAM007")
                .hasSameMessage("Duplicate Username or Email Id or Mobile Number")
                .dataIsNull()
                .timestampIsNotNull();
    }

    @Test(description = "Test #129 - Verify if update user API is working correctly for file when triggered from postman"
            , priority = 7)
    public void verifyUpdatingUser() {
        UserVO createdUser = TestHelper.createUser();

        UserVO updateUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        updateUserRequestVO.setUserId(createdUser.getUserId());
        updateUserRequestVO.setUsername(createdUser.getUsername());
        updateUserRequestVO.setTwoFaEnabled(!createdUser.getTwoFaEnabled());
        updateUserRequestVO.setRoles(List.of(UserRoleEnum.ADMIN.getUserRole()));
        updateUserRequestVO.setActive(!createdUser.getActive());
        ResponseWO<Void> updateUserResponse = UserService.updateUser(updateUserRequestVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(updateUserResponse)
                .hasSameStatus(200)
                .hasSameCode("GEN001")
                .hasSameMessage("Request Processed")
                .timestampIsNotNull()
                .dataIsNull();

        ResponseWO<UserVO> userResponseVO = UserService.getUserByUsername(updateUserRequestVO.getUsername())
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(userResponseVO)
                .hasSameStatus(200)
                .hasSameCode("USR002")
                .hasSameMessage("User Details fetched")
                .timestampIsNotNull()
                .dataIsNotNull();

        UserVO userDetails = userResponseVO.getData();

        Assertions.assertThat(userDetails)
                .as(() -> "User details are not matching with update user request data")
                .isEqualTo(updateUserRequestVO);
    }

    @Test(description = "Test #130 - Verify new user can not be updated with exisiting users details"
            , dependsOnMethods = "verifyCreatingUser"
            , priority = 8)
    public void verifyUpdatingUserWithExistingUserDetails() {
        UserVO user2ResponseVO = TestHelper.createUser();
        this.createdUserIds.add(user2ResponseVO.getUserId());

        UserVO user2UpdateRequestVO = DataProviderUtil.clone(this.createUserRequestVO);
        user2UpdateRequestVO.setUserId(user2ResponseVO.getUserId());
        user2UpdateRequestVO.setUsername(user2ResponseVO.getUsername());

        ResponseWO<Void> updateUserResponse = UserService.updateUser(user2UpdateRequestVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(updateUserResponse)
                .hasSameStatus(400)
                .hasSameCode("IAM007")
                .hasSameMessage("Duplicate Username or Email Id or Mobile Number")
                .timestampIsNotNull()
                .dataIsNull();
    }

    @Test(description = "Test #131 - Verify update user API can not be triggered with incorrect details of client, client-secret and org id"
            , dataProvider = "authHeadersProvider"
            , dependsOnMethods = "verifyCreatingUser"
            , priority = 9)
    public void verifyUpdatingUserWithIncorrectHeaders(Map<String, String> authHeaders) {
        UserVO updateUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        updateUserRequestVO.setUserId(this.userId);
        updateUserRequestVO.setUsername(this.username);
        updateUserRequestVO.setTwoFaEnabled(!this.createUserRequestVO.getTwoFaEnabled());
        updateUserRequestVO.setRoles(List.of(UserRoleEnum.ADMIN.getUserRole()));
        updateUserRequestVO.setActive(!this.createUserRequestVO.getActive());
        ResponseWO<Void> updateUserResponse = UserService.updateUser(updateUserRequestVO, authHeaders, StatusCode.UNAUTHORIZED)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(updateUserResponse)
                .hasSameStatus(401)
                .hasSameCode("AUTH001")
                .hasSameMessage("Client is not authorized to use the Resource")
                .dataIsNull()
                .timestampIsNotNull();
    }

    @Test(description = "Test #132 - Verify if we are unable to update username of user"
            , dependsOnMethods = "verifyCreatingUser"
            , priority = 10)
    public void verifyUpdatingUserName() {
        UserVO updateUserRequestVO = DataProviderUtil.clone(this.createUserRequestVO);
        updateUserRequestVO.setUsername(RandomDataGenerator.generateRandomUniqueEmailId());

        ResponseWO<Void> updateUserResponse = UserService.updateUser(updateUserRequestVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(updateUserResponse)
                .hasSameStatus(200)
                .hasSameCode("GEN001")
                .hasSameMessage("Request Processed")
                .timestampIsNotNull()
                .dataIsNull();

        ResponseWO<UserVO> userResponseVO = UserService.getUserById(updateUserRequestVO.getUserId())
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(userResponseVO)
                .hasSameStatus(200)
                .hasSameCode("USR002")
                .hasSameMessage("User Details fetched")
                .timestampIsNotNull();

        UserVO userDetails = userResponseVO.getData();
        updateUserRequestVO.setUsername(this.username);
        Assertions.assertThat(userDetails)
                .as(() -> "User details are not matching with update user request data")
                .isEqualTo(updateUserRequestVO);
    }

    @Test(description = "Test #136 - Verify user gets 400 error when we hit Fetch user API with incorrect details"
            , priority = 11)
    public void verifyFetchingUserWithIncorrectDetails() {
        ResponseWO<UserVO> userResponseVO = UserService.getUserByUsername("")
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(userResponseVO)
                .hasSameStatus(400)
                .hasSameCode("IAM006")
                .hasSameMessage("Username or UserId is required")
                .timestampIsNotNull()
                .dataIsNull();

        userResponseVO = UserService.getUserById("")
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(userResponseVO)
                .hasSameStatus(400)
                .hasSameCode("IAM006")
                .hasSameMessage("Username or UserId is required")
                .timestampIsNotNull()
                .dataIsNull();
    }

    @Test(description = "Test #137 - Verify user gets 400 error when we hit Create user API with incorrect details"
            , priority = 12)
    public void verifyCreatingUserWithIncorrectDetails() {
        UserVO userVO = new UserVO();
        ResponseWO<Void> responseVO = UserService.createUser(userVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(400)
                .hasSameCode("IAM008")
                .hasSameMessage("Username is required")
                .dataIsNull()
                .timestampIsNotNull();
    }

    @Test(description = "Test #138 - Verify user gets 400 error when we hit update user API with incorrect details"
            , priority = 13)
    public void verifyUpdatingUserWithIncorrectDetails() {
        ResponseWO<Void> responseVO = UserService.updateUser(new UserVO()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(400)
                .hasSameCode("IAM003")
                .hasSameMessage("User not found")
                .dataIsNull()
                .timestampIsNotNull();
    }

    @Test(description = "Test #156 - Verify if Fetch Users API is working correctly when triggered from postman"
            , priority = 14)
    public void verifyFetchingBulkUsers() {
        UserVO user1ResponseVO = TestHelper.createUser();
        String user1Id = user1ResponseVO.getUserId();
        this.createdUserIds.add(user1Id);

        UserVO user2ResponseVO = TestHelper.createUser();
        String user2Name = user2ResponseVO.getUsername();
        this.createdUserIds.add(user2ResponseVO.getUserId());

        BulkUserFetchRequestVO bulkUserFetchRequestVO = new BulkUserFetchRequestVO();
        bulkUserFetchRequestVO.setUserIds(List.of(user1Id));
        bulkUserFetchRequestVO.setUserNames(List.of(user2Name));

        ResponseWO<List<UserVO>> bulkUsersResponseVO = UserService.getUsers(bulkUserFetchRequestVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(bulkUsersResponseVO)
                .hasSameStatus(200)
                .hasSameCode("USR002")
                .hasSameMessage("User Details fetched")
                .timestampIsNotNull()
                .dataIsNotNull();

        Assertions.assertThat(bulkUsersResponseVO.getData())
                .as(() -> "Bulk users size is not expected")
                .hasSize(2)
                .as(() -> "Bulk users details are not expected")
                .containsExactlyInAnyOrder(user1ResponseVO, user2ResponseVO);
    }

    @Test(description = "Test #157 - Verify if Fetch Users API is not working correctly when triggered with invalid Users name or User ID"
            , priority = 15)
    public void verifyFetchingBulkUsersForInvalidUserIdOrUserName() {
        UserVO user1ResponseVO = TestHelper.createUser();
        String user1Id = user1ResponseVO.getUserId();
        TestHelper.deleteUser(user1Id);

        UserVO user2ResponseVO = TestHelper.createUser();
        String user2Name = user2ResponseVO.getUsername();
        TestHelper.deleteUser(user2ResponseVO.getUserId());

        BulkUserFetchRequestVO bulkUserFetchRequestVO = new BulkUserFetchRequestVO();
        bulkUserFetchRequestVO.setUserIds(List.of(user1Id));
        bulkUserFetchRequestVO.setUserNames(List.of(user2Name));

        ResponseWO<List<UserVO>> bulkUsersResponseVO = UserService.getUsers(bulkUserFetchRequestVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(bulkUsersResponseVO)
                .hasSameStatus(200)
                .hasSameCode("USR002")
                .hasSameMessage("User Details fetched")
                .timestampIsNotNull();

        Assertions.assertThat(bulkUsersResponseVO.getData())
                .as(() -> "Bulk users data should be empty")
                .isNullOrEmpty();
    }

    @Test(description = "Test #161 - Verify if Create Users API is working correctly for file when triggered from postman"
            , priority = 16)
    public void verifyCreatingMultipleUsers() {
        List<String> userRoles = List.of(UserRoleEnum.ADMIN.getUserRole(), UserRoleEnum.CLIENT_ADMIN.getUserRole());
        UserVO createUser1RequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUser1RequestVO.setUsername(createUser1RequestVO.getEmailId());
        createUser1RequestVO.setRoles(userRoles);

        UserVO createUser2RequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUser2RequestVO.setUsername(createUser2RequestVO.getEmailId());
        createUser2RequestVO.setRoles(userRoles);

        this.bulkUsersUploadRequest.add(createUser1RequestVO);
        this.bulkUsersUploadRequest.add(createUser2RequestVO);

        ResponseWO<BulkUserUploadResponseVO> bulkUserUploadResponse = UserService.createUsers(this.bulkUsersUploadRequest)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(bulkUserUploadResponse)
                .hasSameStatus(200)
                .hasSameCode("USR001")
                .hasSameMessage("User Registered successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        List<UserVO> bulkUploadedUsers = new ArrayList<>();
        bulkUploadedUsers.add(DataProviderUtil.clone(createUser1RequestVO));
        bulkUploadedUsers.add(DataProviderUtil.clone(createUser2RequestVO));

        BulkUserUploadResponseVO bulkUserUploadResponseData = bulkUserUploadResponse.getData();
        List<CreateUserResponseVO> successUsers = bulkUserUploadResponseData.getSuccessUsers();

        Assertions.assertThat(successUsers)
                .as(() -> "All the bulk uploaded users should be created")
                .isNotNull()
                .isNotEmpty()
                .hasSameSizeAs(this.bulkUsersUploadRequest);

        List<String> failedUsers = bulkUserUploadResponseData.getFailedUsers();
        Assertions.assertThat(failedUsers)
                .as(() -> "Failed users list should be null or empty")
                .isNullOrEmpty();

        List<String> bulkUserIds = successUsers.stream()
                .map(CreateUserResponseVO::getUserId)
                .toList();
        this.createdUserIds.addAll(bulkUserIds);

        for (UserVO userDetails : bulkUploadedUsers) {
            successUsers.stream()
                    .filter(createUserResponseVO -> createUserResponseVO.getUserName().equals(userDetails.getUsername()))
                    .findFirst()
                    .ifPresent(createUserResponseVO -> userDetails.setUserId(createUserResponseVO.getUserId()));
        }

        BulkUserFetchRequestVO bulkUserFetchRequestVO = new BulkUserFetchRequestVO();
        bulkUserFetchRequestVO.setUserIds(bulkUserIds);

        ResponseWO<List<UserVO>> bulkUsersResponseVO = UserService.getUsers(bulkUserFetchRequestVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(bulkUsersResponseVO)
                .hasSameStatus(200)
                .hasSameCode("USR002")
                .hasSameMessage("User Details fetched")
                .timestampIsNotNull()
                .dataIsNotNull();

        Assertions.assertThat(bulkUsersResponseVO.getData())
                .as(() -> "Bulk users size is not expected")
                .hasSameSizeAs(bulkUserIds)
                .as(() -> "Bulk users details are not expected")
                .containsExactlyInAnyOrderElementsOf(bulkUploadedUsers);
    }

    @Test(description = "Test #162 - Verify new Users can not be created with existing Users details"
            , dependsOnMethods = "verifyCreatingMultipleUsers"
            , priority = 17)
    public void verifyCreatingMultipleUsersWithExistingUserDetails() {
        ResponseWO<BulkUserUploadResponseVO> bulkUserUploadResponse = UserService.createUsers(this.bulkUsersUploadRequest)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(bulkUserUploadResponse)
                .hasSameStatus(200)
                .hasSameCode("USR001")
                .hasSameMessage("User Registered successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        BulkUserUploadResponseVO bulkUserUploadResponseData = bulkUserUploadResponse.getData();
        List<CreateUserResponseVO> successUsers = bulkUserUploadResponseData.getSuccessUsers();

        Assertions.assertThat(successUsers)
                .as(() -> "Success users list should be null or empty")
                .isNullOrEmpty();

        List<String> failedUsers = bulkUserUploadResponseData.getFailedUsers();
        Assertions.assertThat(failedUsers)
                .as(() -> "Any of the bulk uploaded user should not be created")
                .isNotNull()
                .isNotEmpty()
                .hasSameSizeAs(this.bulkUsersUploadRequest)
                .as(() -> "Failed users should contain all the usernames of the bulk users upload request")
                .containsExactlyInAnyOrderElementsOf(this.bulkUsersUploadRequest.stream().map(UserVO::getUsername).toList());
    }

    @Test(description = "Test #164 - Verify if we are unable to create Users without Usersname"
            , priority = 18)
    public void verifyCreatingMultipleUsersWithoutUsername() {
        List<String> userRoles = List.of(UserRoleEnum.ADMIN.getUserRole(), UserRoleEnum.CLIENT_ADMIN.getUserRole());
        UserVO createUser1RequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUser1RequestVO.setRoles(userRoles);

        UserVO createUser2RequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUser2RequestVO.setRoles(userRoles);

        List<UserVO> bulkUsersUploadRequestWithoutUsername = List.of(createUser1RequestVO, createUser2RequestVO);

        ResponseWO<BulkUserUploadResponseVO> bulkUserUploadResponse = UserService.createUsers(bulkUsersUploadRequestWithoutUsername)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(bulkUserUploadResponse)
                .hasSameStatus(200)
                .hasSameCode("USR001")
                .hasSameMessage("User Registered successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        BulkUserUploadResponseVO bulkUserUploadResponseData = bulkUserUploadResponse.getData();
        List<CreateUserResponseVO> successUsers = bulkUserUploadResponseData.getSuccessUsers();

        Assertions.assertThat(successUsers)
                .as(() -> "Success users list should be null or empty")
                .isNullOrEmpty();

        List<String> failedUsers = bulkUserUploadResponseData.getFailedUsers();
        Assertions.assertThat(failedUsers)
                .as(() -> "Any of the bulk uploaded user should not be created")
                .isNotNull()
                .isNotEmpty()
                .hasSameSizeAs(bulkUsersUploadRequestWithoutUsername)
                .as(() -> "Failed users should contain only nulls as usernames were not provided in the request")
                .containsOnlyNulls();
    }

    @Test(description = "Test #165 - Verify we are unable to create Users with any mandatory field missing"
            , priority = 19)
    public void verifyCreatingBulkUsersWithAnyMandatoryFieldMissing() {
        List<String> userRoles = List.of(UserRoleEnum.ADMIN.getUserRole(), UserRoleEnum.CLIENT_ADMIN.getUserRole());
        UserVO createUser1RequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUser1RequestVO.setUsername(createUser1RequestVO.getEmailId());
        createUser1RequestVO.setEmailId(null);
        createUser1RequestVO.setRoles(userRoles);

        UserVO createUser2RequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUser2RequestVO.setUsername(createUser2RequestVO.getEmailId());

        List<UserVO> bulkUsersUploadRequestWithMandatoryFieldMissing = List.of(createUser1RequestVO, createUser2RequestVO);

        ResponseWO<BulkUserUploadResponseVO> bulkUserUploadResponse = UserService.createUsers(bulkUsersUploadRequestWithMandatoryFieldMissing)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(bulkUserUploadResponse)
                .hasSameStatus(200)
                .hasSameCode("USR001")
                .hasSameMessage("User Registered successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        BulkUserUploadResponseVO bulkUserUploadResponseData = bulkUserUploadResponse.getData();
        List<CreateUserResponseVO> successUsers = bulkUserUploadResponseData.getSuccessUsers();

        Assertions.assertThat(successUsers)
                .as(() -> "Success users list should be null or empty")
                .isNullOrEmpty();

        List<String> failedUsers = bulkUserUploadResponseData.getFailedUsers();
        Assertions.assertThat(failedUsers)
                .as(() -> "Any of the bulk uploaded user should not be created")
                .isNotNull()
                .isNotEmpty()
                .hasSameSizeAs(bulkUsersUploadRequestWithMandatoryFieldMissing)
                .as(() -> "Failed users should contain all the usernames of the bulk users upload request")
                .containsExactlyInAnyOrderElementsOf(bulkUsersUploadRequestWithMandatoryFieldMissing.stream()
                        .map(UserVO::getUsername)
                        .toList());
    }
    @Test(description = "Test #366 - Test to validate the create user API for the Corporates role."
            , priority = 20)
    public void verifyCreatingCorporateRole() {
        UserVO createUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUserRequestVO.setTwoFaEnabled(true);
        createUserRequestVO.setUsername(RandomDataGenerator.generateRandomFirstName() + RandomDataGenerator.getUuid());
        createUserRequestVO.setDefaultPassword(RandomDataGenerator.generateRandomPassword());
        createUserRequestVO.setActive(true);
        List<String> userRoles = List.of(UserRoleEnum.CORPORATES.getUserRole());
        createUserRequestVO.setRoles(userRoles);
        ResponseWO<CreateUserResponseVO> createUserResponseVOResponseWO = UserService.createUser(createUserRequestVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(createUserResponseVOResponseWO)
                .hasSameMessage(ApiMessageConstants.USER_REGISTERED_SUCCESSFULLY)
                .hasSameCode("USR001")
                .timestampIsNotNull();
        FrameworkAssertions.assertThat(createUserResponseVOResponseWO.getData())
                .hasSameUserName(createUserRequestVO.getUsername())
                .userIdIsNotNull();
        this.createdUserIds.add(createUserResponseVOResponseWO.getData().getUserId());
    }

    @Test(description = "Test #367 - Test to validate the create user API for the clientadmin role.."
            , priority = 20)
    public void verifyCreatingClientAdminRole() {
        UserVO createUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUserRequestVO.setTwoFaEnabled(true);
        createUserRequestVO.setUsername(RandomDataGenerator.generateRandomFirstName() + RandomDataGenerator.getUuid());
        createUserRequestVO.setDefaultPassword(RandomDataGenerator.generateRandomPassword());
        createUserRequestVO.setActive(true);
        List<String> userRoles = List.of(UserRoleEnum.CLIENT_ADMIN.getUserRole());
        createUserRequestVO.setRoles(userRoles);
        ResponseWO<CreateUserResponseVO> createUserResponseVOResponseWO = UserService.createUser(createUserRequestVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(createUserResponseVOResponseWO)
                .hasSameMessage(ApiMessageConstants.USER_REGISTERED_SUCCESSFULLY)
                .hasSameCode("USR001")
                .timestampIsNotNull();
        FrameworkAssertions.assertThat(createUserResponseVOResponseWO.getData())
                .hasSameUserName(createUserRequestVO.getUsername())
                .userIdIsNotNull();
        this.createdUserIds.add(createUserResponseVOResponseWO.getData().getUserId());
    }

    @Test(description = "Test #368 - Test to validate the create user API for the Client role."
            , priority = 20)
    public void verifyCreatingClientUserRole() {
        UserVO createUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUserRequestVO.setTwoFaEnabled(true);
        createUserRequestVO.setUsername(RandomDataGenerator.generateRandomFirstName() + RandomDataGenerator.getUuid());
        createUserRequestVO.setDefaultPassword(RandomDataGenerator.generateRandomPassword());
        createUserRequestVO.setActive(true);
        List<String> userRoles = List.of(UserRoleEnum.CLIENT_USERS.getUserRole());
        createUserRequestVO.setRoles(userRoles);
        ResponseWO<CreateUserResponseVO> createUserResponseVOResponseWO = UserService.createUser(createUserRequestVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(createUserResponseVOResponseWO)
                .hasSameMessage(ApiMessageConstants.USER_REGISTERED_SUCCESSFULLY)
                .hasSameCode("USR001")
                .timestampIsNotNull();
        FrameworkAssertions.assertThat(createUserResponseVOResponseWO.getData())
                .hasSameUserName(createUserRequestVO.getUsername())
                .userIdIsNotNull();
        this.createdUserIds.add(createUserResponseVOResponseWO.getData().getUserId());
    }

    @Test(description = "Test #369 - Test to validate the create user API for the admin role.."
            , priority = 20)
    public void verifyCreatingAdminRole() {
        UserVO createUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUserRequestVO.setTwoFaEnabled(true);
        createUserRequestVO.setUsername(RandomDataGenerator.generateRandomFirstName() + RandomDataGenerator.getUuid());
        createUserRequestVO.setDefaultPassword(RandomDataGenerator.generateRandomPassword());
        createUserRequestVO.setActive(true);
        List<String> userRoles = List.of(UserRoleEnum.ADMIN.getUserRole());
        createUserRequestVO.setRoles(userRoles);
        ResponseWO<CreateUserResponseVO> createUserResponseVOResponseWO = UserService.createUser(createUserRequestVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(createUserResponseVOResponseWO)
                .hasSameMessage(ApiMessageConstants.USER_REGISTERED_SUCCESSFULLY)
                .hasSameCode("USR001")
                .timestampIsNotNull();
        FrameworkAssertions.assertThat(createUserResponseVOResponseWO.getData())
                .hasSameUserName(createUserRequestVO.getUsername())
                .userIdIsNotNull();
        this.createdUserIds.add(createUserResponseVOResponseWO.getData().getUserId());
    }

    @Test(description = "Test #370 - Test to validate the create user API for the Borrower role."
            , priority = 20)
    public void verifyCreatingBorrowerRole() {
        UserVO createUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUserRequestVO.setTwoFaEnabled(true);
        createUserRequestVO.setUsername(RandomDataGenerator.generateRandomFirstName() + RandomDataGenerator.getUuid());
        createUserRequestVO.setDefaultPassword(RandomDataGenerator.generateRandomPassword());
        createUserRequestVO.setActive(true);
        List<String> userRoles = List.of(UserRoleEnum.BORROWER.getUserRole());
        createUserRequestVO.setRoles(userRoles);
        ResponseWO<CreateUserResponseVO> createUserResponseVOResponseWO = UserService.createUser(createUserRequestVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(createUserResponseVOResponseWO)
                .hasSameMessage(ApiMessageConstants.USER_REGISTERED_SUCCESSFULLY)
                .hasSameCode("USR001")
                .timestampIsNotNull();
        FrameworkAssertions.assertThat(createUserResponseVOResponseWO.getData())
                .hasSameUserName(createUserRequestVO.getUsername())
                .userIdIsNotNull();
        this.createdUserIds.add(createUserResponseVOResponseWO.getData().getUserId());
    }

    @AfterClass
    public void deleteCreatedUser() {
        for (String createdUserId : this.createdUserIds) {
            try {
                TestHelper.deleteUser(createdUserId);
            } catch (Exception e) {
                log.warn("Could not delete user: {}. Ignoring the exception", createdUserId);
            }
        }
    }

    @DataProvider(name = "authHeadersProvider", propagateFailureAsTestFailure = true)
    private Object[][] authHeadersProvider() {
        EnvironmentConfig environmentConfig = ConfigFactory.getEnvironmentConfig();
        String keyCloakOrganizationId = environmentConfig.getKeyCloakOrganizationId();
        String keyCloakClientId = environmentConfig.getKeyCloakClientId();
        String keyCloakClientSecret = environmentConfig.getKeyCloakClientSecret();
        return new Object[][]{
                {Map.of()},
                {Map.of(HeaderName.ORG_ID, "", HeaderName.X_CLIENT_ID, "", HeaderName.CLIENT_SECRET, "")},
                {Map.of(HeaderName.ORG_ID, keyCloakOrganizationId, HeaderName.X_CLIENT_ID, keyCloakClientId, HeaderName.CLIENT_SECRET, "")},
                {Map.of(HeaderName.ORG_ID, keyCloakOrganizationId, HeaderName.X_CLIENT_ID, "", HeaderName.CLIENT_SECRET, keyCloakClientSecret)},
                {Map.of(HeaderName.ORG_ID, "", HeaderName.X_CLIENT_ID, keyCloakClientId, HeaderName.CLIENT_SECRET, keyCloakClientSecret)}
        };
    }
}
