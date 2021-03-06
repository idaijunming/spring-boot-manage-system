package com.demo.rbac.control.impl;


import com.demo.rbac.authorization.annotation.NoAuthorization;
import com.demo.rbac.control.AccessControl;
import com.demo.rbac.control.BaseControl;
import com.demo.rbac.model.Access;
import com.demo.rbac.service.AccessService;
import com.demo.rbac.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AccessControlImpl extends BaseControl<Access> implements AccessControl {

    @NoAuthorization
    @GetMapping(value = "accesses")
    @Override
    public ResponseEntity selectAll() {
        log.info("AccessControlImpl::selectAll::");
        AccessService service = (AccessService) getService();
        return new ResponseEntity<>(service.selectAll(), HttpStatus.OK);
    }

    @Autowired
    @Override
    public void setService(BaseService<Access> service) {
        super.setService(service);
    }
}
