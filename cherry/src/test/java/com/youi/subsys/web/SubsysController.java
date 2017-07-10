package com.youi.subsys.web;

import com.youi.core.export.Exportor;
import com.youi.core.export.TableModel;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.query.PropertyFilter;
import com.youi.core.spring.MessageHelper;
import com.youi.subsys.persistence.domain.PartyEntity;
import com.youi.subsys.persistence.manager.PartyEntityManager;
import com.youi.subsys.persistence.domain.SubsysInfo;
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
@RequestMapping("subsys")
public class SubsysController {

    private SubsysManager subsysManager;

    private PartyEntityManager partyEntityManager;

    private MessageHelper messageHelper;

    private Exportor exportor;

    private BeanMapper beanMapper = new BeanMapper();

    private static String template="/template/";

    @RequestMapping("subsys-list")
    public String list(@ModelAttribute Page page,
                       @RequestParam Map<String, Object> parameterMap, Model model) {
        List<PropertyFilter> propertyFilters = PropertyFilter
                .buildFromMap(parameterMap);
        page = subsysManager.pagedQuery(page, propertyFilters);

        model.addAttribute("page", page);

        return template+"subsys/subsys-list";
    }

    @RequestMapping("subsys-input")
    public String input(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        List<PartyEntity> partyEntities = partyEntityManager.getAll();
        model.addAttribute("partyEntities", partyEntities);
        if (id != null) {
            SubsysInfo subsysInfo = subsysManager.get(id);
            model.addAttribute("model", subsysInfo);
        }

        return template+"subsys/subsys-input";
    }

    @RequestMapping("subsys-save")
    public String save(@ModelAttribute SubsysInfo subsysInfo,
                       @RequestParam("developed_department_id") Long developed_department_id,
                       @RequestParam("use_department_id") Long use_department_id,
                       RedirectAttributes redirectAttributes) {
        SubsysInfo dest = null;
        Long id = subsysInfo.getId();

        if (id != null) {
            dest = subsysManager.get(id);
            beanMapper.copy(subsysInfo, dest);
        } else {
            dest = subsysInfo;
        }

        dest.setDeveloped_department(partyEntityManager.get(developed_department_id));
        dest.setUse_department(partyEntityManager.get(use_department_id));
        subsysManager.save(dest);
        messageHelper.addFlashMessage(redirectAttributes,
                "保存成功");

        return "redirect:/subsys/subsys-list.do";
    }

    @RequestMapping("subsys-remove")
    public String remove(@RequestParam("selectedItem") List<Long> selectedItem,
                         RedirectAttributes redirectAttributes) {
        subsysManager .removeAll(subsysManager.findByIds(selectedItem));
        messageHelper.addFlashMessage(redirectAttributes, "删除成功");

        return "redirect:/subsys/subsys-list.do";
    }

    @RequestMapping("subsys-export")
    public void export(@ModelAttribute Page page,
                       @RequestParam Map<String, Object> parameterMap,
                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<PropertyFilter> propertyFilters = PropertyFilter
                .buildFromMap(parameterMap);

        page = subsysManager.pagedQuery(page, propertyFilters);

        List<SubsysInfo> auditBases = (List<SubsysInfo>) page.getResult();
        List<Map<String,String>> rlist = new ArrayList<Map<String,String>>(auditBases.size());
        if(auditBases!=null){
             for(int i=0;i<auditBases.size();i++){
                 SubsysInfo s=  auditBases.get(i);
                 Map<String,String> m=new HashMap<String, String>();
                 m.put("id",String.valueOf(s.getId()));
                 m.put("name",s.getName());
                 m.put("developed_department_name",s.getDeveloped_department().getName());
                 m.put("use_department_name",s.getUse_department().getName());
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                 m.put("online_time",sdf.format(s.getOnline_time()) );
                 rlist.add(m);
             }
        }


        TableModel tableModel = new TableModel();
        tableModel.setName("ywxt");
        tableModel.addHeaders("id", "name", "developed_department_name", "use_department_name","online_time");
        tableModel.setData(rlist);
        exportor.export(request, response, tableModel);
    }

    @Resource
    public void setExportor(Exportor exportor) {
        this.exportor = exportor;
    }


    @Resource
    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Resource
    public void setSubsysManager(SubsysManager subsysManager) {
        this.subsysManager = subsysManager;
    }
    @Resource
    public void setPartyEntityManager(PartyEntityManager partyEntityManager) {
        this.partyEntityManager = partyEntityManager;
    }
}
