package com.example.makroid.tellit.models;

import java.io.Serializable;

/**
 * Created by joginderpal on 29-04-2017.
 */
public class recent_story {

    int id;
    String title;
    int categoryId;
    String image_path;
    int initiator_id;
    String date_created;
    int views;
    int votes;
    String category;
    String initiator_full_name;
    String initiator_image_path;


    // Only do upto image path only first.


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public int getInitiator_id() {
        return initiator_id;
    }

    public void setInitiator_id(int initiator_id) {
        this.initiator_id = initiator_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInitiator_full_name() {
        return initiator_full_name;
    }

    public void setInitiator_full_name(String initiator_full_name) {
        this.initiator_full_name = initiator_full_name;
    }

    public String getInitiator_image_path() {
        return initiator_image_path;
    }

    public void setInitiator_image_path(String initiator_image_path) {
        this.initiator_image_path = initiator_image_path;
    }
}
