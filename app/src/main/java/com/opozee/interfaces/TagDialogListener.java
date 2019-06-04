package com.opozee.interfaces;

import com.opozee.pojo.TagUsersResponse;

import java.util.List;

public interface TagDialogListener {
    void onDone(List<TagUsersResponse.GetAllUser> usersList);
    void onCancel();
}
