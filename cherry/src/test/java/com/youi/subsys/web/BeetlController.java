package com.youi.subsys.web;

import com.youi.core.export.Exportor;
import com.youi.core.export.TableModel;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.query.PropertyFilter;
import com.youi.core.spring.MessageHelper;
import com.youi.subsys.persistence.domain.PartyEntity;
import com.youi.subsys.persistence.domain.SubsysInfo;
import com.youi.subsys.persistence.manager.PartyEntityManager;
import com.youi.subsys.persistence.manager.SubsysManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 16/6/25.
 */
@Controller
@RequestMapping("beetl")
public class BeetlController {




    @RequestMapping("list")
    public String list() {
        return "/template/beetl";
    }

}
