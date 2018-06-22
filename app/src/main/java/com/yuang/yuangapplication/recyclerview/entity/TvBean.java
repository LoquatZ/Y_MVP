package com.yuang.yuangapplication.recyclerview.entity;

import java.io.Serializable;
import java.util.List;
public class TvBean {

    private int page;
    private int pageCount;
    private int size;
    private int total;
    private String icon;
    private List<DataBean> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        /**
         * nick : ???
         * video : http://thumb.quanmin.tv/3851712.mp4?t=1480732500
         * screen : 0
         * grade : 0
         * avatar : http://image.quanmin.tv/avatar/a815ddc9b0ec08f5a5c274006211387c?imageView2/2/w/300/
         * thumb : http://snap.quanmin.tv/3851712-1480732780-247.jpg?imageView2/2/w/390/
         * level : 0
         * recommend_image :
         * like : 0
         * uid : 3851712
         * view : 10480
         * love_cover :
         * category_name : ????
         * default_image :
         * category_id : 4
         * follow : 8255
         * beauty_cover :
         * slug :
         * title : ???~
         * category_slug : beauty
         * weight : 1656780
         * start_time : 2016-12-03 08:33:17
         * intro :
         * status : 1
         * announcement : ??????????????
         * create_at : 2016-12-03 08:33:17
         * play_at : 2016-12-03 08:33:17
         * video_quality :
         * app_shuffling_image :
         * year_type :
         * hidden :
         */

        private String recommend_image;
        private String announcement;
        private String title;
        private String create_at;
        private String intro;
        private String video;
        private int screen;
        private String push_ip;
        private String love_cover;
        private String category_id;
        private String video_quality;
        private String like;
        private String default_image;
        private String slug;
        private String weight;
        private String status;
        private String level;
        private String avatar;
        private String uid;
        private String play_at;
        private String view;
        private String category_slug;
        private String nick;
        private String beauty_cover;
        private String app_shuffling_image;
        private String start_time;
        private int follow;
        private String category_name;
        private String thumb;
        private String grade;
        private boolean hidden;
        private String icontext;

        public String getRecommend_image() {
            return recommend_image;
        }

        public void setRecommend_image(String recommend_image) {
            this.recommend_image = recommend_image;
        }

        public String getAnnouncement() {
            return announcement;
        }

        public void setAnnouncement(String announcement) {
            this.announcement = announcement;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public int getScreen() {
            return screen;
        }

        public void setScreen(int screen) {
            this.screen = screen;
        }

        public String getPush_ip() {
            return push_ip;
        }

        public void setPush_ip(String push_ip) {
            this.push_ip = push_ip;
        }

        public String getLove_cover() {
            return love_cover;
        }

        public void setLove_cover(String love_cover) {
            this.love_cover = love_cover;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getVideo_quality() {
            return video_quality;
        }

        public void setVideo_quality(String video_quality) {
            this.video_quality = video_quality;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getDefault_image() {
            return default_image;
        }

        public void setDefault_image(String default_image) {
            this.default_image = default_image;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPlay_at() {
            return play_at;
        }

        public void setPlay_at(String play_at) {
            this.play_at = play_at;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getCategory_slug() {
            return category_slug;
        }

        public void setCategory_slug(String category_slug) {
            this.category_slug = category_slug;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getBeauty_cover() {
            return beauty_cover;
        }

        public void setBeauty_cover(String beauty_cover) {
            this.beauty_cover = beauty_cover;
        }

        public String getApp_shuffling_image() {
            return app_shuffling_image;
        }

        public void setApp_shuffling_image(String app_shuffling_image) {
            this.app_shuffling_image = app_shuffling_image;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public boolean isHidden() {
            return hidden;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }

        public String getIcontext() {
            return icontext;
        }

        public void setIcontext(String icontext) {
            this.icontext = icontext;
        }
    }
}
