package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by GZR on 2016/11/17.
 */
//put bean in the bean factory
@Controller
@RequestMapping("/seckill")//url://module/resource/{id}/
public class SeckillController {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Resource
    private SeckillService seckillService;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list(Model model){
        //Get list view
        logger.error("come list","hahah");
        List<Seckill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);

        //list.jsp+model=model and view
        return "list";
    }

    @RequestMapping(value="/{seckillId}/detail",method =RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){

        if(seckillId==null){
            return "redirect:/seckill/list";
        }
        Seckill seckill=seckillService.getById(seckillId);
        if(seckill==null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    //ajax json
    @RequestMapping(value="/{seckillId}/exposer",method =RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer=seckillService.exportSeckillUrl(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result=new SeckillResult<Exposer>(false,e.getMessage());
        }

        return null;
    }

    @RequestMapping(value="/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,@PathVariable("md5") String md5,@CookieValue(value="killPhone",required = false) Long phone){

        if(phone==null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }

        SeckillResult<SeckillExecution> result;
        try {

            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        }catch (RepeatKillException e1){
            SeckillExecution execution=new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(false,execution);
        }catch (SeckillCloseException e2){
            SeckillExecution execution=new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(false,execution);
        }
        catch (Exception e){
            logger.info(e.getMessage(),e);
            SeckillExecution execution=new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false,execution);
        }
    }

    @RequestMapping(value="/time/now",method = RequestMethod.GET)
    public SeckillResult<Long> time(){
        Date date=new Date();
        return new SeckillResult<Long>(true,date.getTime());
    }

}
