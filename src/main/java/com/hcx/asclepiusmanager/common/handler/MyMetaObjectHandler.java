package com.hcx.asclepiusmanager.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Supplier;

import static com.hcx.asclepiusmanager.common.constant.CustConstant.*;

/**
 * @ClassName MyMetaObjectHandler
 * @Author hcx
 * @Date 2022/2/9 16:05
 * @Version 1.0
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时的填充策略
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill.....");
        // setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject
        fillValue(metaObject, CREATED_BY, () -> getCurrentOperator());
        fillValue(metaObject, UPDATED_BY, () -> getCurrentOperator());
        fillValue(metaObject, CREATED_DATE, () -> getDateValue(metaObject.getSetterType(CREATED_DATE)));
        fillValue(metaObject, UPDATED_DATE, () -> getDateValue(metaObject.getSetterType(UPDATED_DATE)));
        log.info("finish insert fill.....");
    }

    private Object getCurrentOperator() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof JwtUser) {
            JwtUser userPrincipal = (JwtUser) authentication.getPrincipal();
            if (authentication == null ||
                    !authentication.isAuthenticated() ||
                    authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }
            return String.valueOf(userPrincipal.getId());
        }else {
            return "";
        }
    }

    /**
     * 更新时的填充策略
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill.....");
        fillValue(metaObject, UPDATED_BY, () -> getCurrentOperator());
        fillValue(metaObject, UPDATED_DATE, () -> getDateValue(metaObject.getSetterType(UPDATED_DATE)));
    }

    private void fillValue(MetaObject metaObject, String fieldName, Supplier<Object> valueSupplier) {
        if (!metaObject.hasGetter(fieldName)) {
            return;
        }
        Object sidObj = metaObject.getValue(fieldName);
        if (sidObj == null && metaObject.hasSetter(fieldName) && valueSupplier != null) {
            setFieldValByName(fieldName, valueSupplier.get(), metaObject);
        }
    }

    private Object getDateValue(Class<?> setterType) {
        if (Date.class.equals(setterType)) {
            return new Date();
        } else if (LocalDateTime.class.equals(setterType)) {
            return LocalDateTime.now();
        }
        return null;
    }

}

