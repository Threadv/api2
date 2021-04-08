package com.ifenghui.storybookapi.app.transaction.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class GetShareInfoResponse extends ApiResponse {


    String shareUrl;
    String shareIcon;
    String shareTitle;
    String shareContent;
    String shareMomentsContent;
    String shareRule;

    public String getShareMomentsContent() {
        return shareMomentsContent;
    }

    public void setShareMomentsContent(String shareMomentsContent) {
        this.shareMomentsContent = shareMomentsContent;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareRule() {
        return shareRule;
    }

    public void setShareRule(String shareRule) {
        this.shareRule = shareRule;
    }
}
