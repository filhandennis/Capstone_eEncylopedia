package com.develop.filhan.eencyclopediaone;

import java.util.List;

/**
 * Created by ASUS on 04/05/2018.
 */

public class MenuFirebaseModel {
    private int menuId;
    private String cover;
    private List<ViewModel> Views;
    private List<CommentModel> Comments;
    private List<FavModel> Favorites;

    public MenuFirebaseModel(int menuId, String cover, List<ViewModel> views, List<CommentModel> comments, List<FavModel> favorites) {
        this.menuId = menuId;
        this.cover = cover;
        Views = views;
        Comments = comments;
        Favorites = favorites;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<ViewModel> getViews() {
        return Views;
    }

    public void setViews(List<ViewModel> views) {
        Views = views;
    }

    public List<CommentModel> getComments() {
        return Comments;
    }

    public void setComments(List<CommentModel> comments) {
        Comments = comments;
    }

    public List<FavModel> getFavorites() {
        return Favorites;
    }

    public void setFavorites(List<FavModel> favorites) {
        Favorites = favorites;
    }

    public int getViewsNum() {
        return Views.size();
    }

    public int getCommentsNum() {
        return Comments.size();
    }

    public int getFavoritesNum() {
        return Favorites.size();
    }
}
