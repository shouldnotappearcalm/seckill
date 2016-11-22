/**
 * Created by GZR on 2016/11/21.
 */
var seckill={
    //packing seckill url of ajax
    URL:{
        now:function () {
            return '/seckill/time/now';
        },
        exposer:function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        execution:function (seckillId,md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }
    },
    //validator phone number
    validatePhone:function (phone) {
      if(phone && phone.length==11 && !isNaN(phone)){
            return true;
        }
        return false;
    },
    handlerSeckill:function (seckillId,node) {
        //handle execute logic ,control view logic,execute seckill
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">begin seckill</button>');
        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            if(result&&result['success']){
                var exposer=result['data'];
                if(exposer['exposed']){
                    //open seckill
                    //get seckill url
                    var md5=exposer['md5'];
                    var seckillUrl=seckill.URL.execution(seckillId,md5);
                    console.log("killUrl:"+seckillUrl);
                    //forbidden repeat click

                    $('#killBtn').one('click',function () {
                        //execute seckill request
                        //1.forbidden button click event at first
                        console.log('first click');
                        $(this).addClass('disabled');
                        //send seckill request
                        $.post(seckillUrl,{},function (result) {
                            var killResult=result['data'];
                            var state=killResult['state'];
                            var stateInfo=killResult['stateInfo'];
                            //3.show seckill result
                            node.html('<span class="label label-success">'+stateInfo+'</span>');
                        });
                    });
                    node.show();
                }else{
                    //not open seckill
                    var now=exposer['now'];
                    var start=exposer['start'];
                    var end=exposer['end'];
                    //re calculate timing logic
                    seckill.countdown(seckillId,now,start,end);
                }
            }else{
                console.log('result'+result);//TODO
            }
        });
    },
    countdown:function (seckillId,nowTime,startTime,endTime) {
        var seckillBox=$('#seckill-box');
        console.log(nowTime);//TODO
        //judge time
        if(nowTime>endTime){
            //seckill is end
            seckillBox.html('seckill is end');
        }else if(nowTime<startTime){
            //seckill is not open yet,bind timing event
            var killTime=new Date(startTime+1000);
            console.log(killTime);//TODO
            seckillBox.countdown(killTime,function (event) {
                //control time format
                var format=event.strftime('seckill countdown:%D day %H hour %M minute %S second');
                seckillBox.html(format);
                //timing is end,callback event
            }).on('finish.countdown',function () {
                //get seckill address,control reality logic,execute seckill
                seckill.handlerSeckill(seckillId,seckillBox);
            });
        }else{
            //seckill start
            seckill.handlerSeckill(seckillId,seckillBox);
        }
    },

    //seckill logic of detail page
    detail:{
        //init detail page
        init:function (params) {
            //phone number validator,timing interaction
            //planning our process of interaction
            //find number from cookie
            var killPhone=$.cookie('killPhone');
            var startTime=params['startTime'];
            var endTime=params['endTime'];
            var seckillId=params['seckillId'];
            //validator phone number
            if(!seckill.validatePhone(killPhone)){
                //bind phone
                //control output
                var killPhoneModal=$('#killPhoneModal');
                //view pop-up layer
                killPhoneModal.modal({
                   show:true,//show pop-up layer
                    backdrop:'static',//forbidden location closed
                    keyboard:false//close keyboard
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone=$('#killPhoneKey').val();
                    if(seckill.validatePhone(inputPhone)){
                        //put number in cookie
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //refresh page
                        window.location.reload();
                    }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                });
            }
            //already login
            //timing interaction
            $.get(seckill.URL.now(),function (result) {
                if(result && result['success']){
                    var nowTime=result['data'];
                    //judge time
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }
            });
        }
    }
}