package com.opozeeApp.interfaces;

import com.opozeeApp.pojo.TagUsersResponse;

import java.util.List;

public interface TagDialogListener {
    void onDone(List<TagUsersResponse.GetAllUser> usersList);
    void onCancel();
}
