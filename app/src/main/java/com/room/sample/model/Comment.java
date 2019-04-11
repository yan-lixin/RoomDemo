package com.room.sample.model;

import java.util.Date;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description:
 */
public interface Comment {
    int getId();
    int getProductId();
    String getText();
    Date getPostedAt();

}
