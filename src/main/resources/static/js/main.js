$(document).ready(function(){

    var imgLoader = new ImgLoader();
    imgLoader.progress(function(a,b,c){
        //count=Math.round((b+c)*100/(a||1));
    });
    imgLoader.completed(function(a,b,c){
           init1();
    });
    imgLoader.start();
    $(window).on('resize',resizeCanvas);
    resizeCanvas();


    $(".btn_ios").on("click",function () {
        window.open("itms-apps://itunes.apple.com/cn/app/id1388163896");
    });

    $(".btn_and").on("click",function () {
        window.location.href="https://a.app.qq.com/o/simple.jsp?pkgname=com.example.x.hotelmanagement&channel=0002160650432d595942&fromcase=60001";
    });



    function init1(){
        $(".page").show();
    }

    function resizeCanvas()
    {
        var winWidth=document.documentElement.clientWidth||document.body.clientWidth;
        var winHeight=document.documentElement.clientHeight||document.body.clientHeight;


        if(winWidth>winHeight)
        {
            $("#landscape").show();
            return;
        }
        $("#landscape").hide();
    }


});


