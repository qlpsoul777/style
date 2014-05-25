package com.qlp.user.service.impl;

import com.qlp.user.service.RoleService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by qlp on 14-5-22.
 */
@Component
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
}
