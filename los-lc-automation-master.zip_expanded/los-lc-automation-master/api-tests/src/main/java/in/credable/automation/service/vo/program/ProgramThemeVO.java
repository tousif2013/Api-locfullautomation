package in.credable.automation.service.vo.program;

import lombok.Data;

@Data


public class ProgramThemeVO {

    private Long id;

    private Long programId;

    private String headerImage;

    private String headerImageUrl;

    private String headerSkinColor;

    private String primaryButtonColor;

    private String secondaryButtonColor;

    private String fontName;

    private String footerSkinColor;

    private String footerImage;

    private String footerImageUrl;

    private String footerText;

    private String footerTextAlignment;

}
