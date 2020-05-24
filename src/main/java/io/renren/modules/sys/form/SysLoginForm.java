/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录表单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysLoginForm {
    private String username;
    private String password;
    private String captcha;
    private String uuid;


}
