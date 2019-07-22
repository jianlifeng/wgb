/**
 * Created by skua on 14-12-17.
 */
//var cnd="http://s.flyfinger.com/picRepo/msi/WeChatRed5/";
var cnd="";
function ImgLoader(){
    var property=[
        cnd+"../img/bg.jpg",
        cnd+"../img/btn_ios.png",
        cnd+"../img/btn_and.png"
    ];

    var onloadedcompleted	,
        onloading			,
        NUM_ELEMENTS		,
        NUM_LOADED = 0		,
        NUM_ERROR = 0		,
        TempProperty = {}	,
        LOADED_THEMES={}	,
        loadList = []

    if(typeof(property) == 'string'){
        NUM_ELEMENTS=1;
        loadList[0]=property;
    }else{
        NUM_ELEMENTS=property.length;
        loadList=property;
    }
    this.assets=TempProperty;
    this.asset=LOADED_THEMES;
    this.completed=function(callback){
        onloadedcompleted=callback;
    };
    this.progress=function(callback){
        onloading=callback;
    };
    this.start=function(){
        for(var i=0;i<NUM_ELEMENTS;i++){
            load(loadList[i],imageLoaded,imageLoadError);
        }
        return TempProperty;
    };
    function load(img,loaded,error){
        var image=new Image();
        image.onload=loaded;
        image.onerror=error;
        image.src=img;
        TempProperty[img]=image;
    };
    function imageLoaded(){
        var imgsrc=this.getAttribute("src");
        TempProperty[imgsrc].loaded=true;
        NUM_LOADED++;

        if(NUM_LOADED+NUM_ERROR==NUM_ELEMENTS){
            typeof(onloadedcompleted) =='function' && onloadedcompleted(NUM_ELEMENTS,NUM_LOADED,NUM_ERROR);
        }else{
            typeof(onloading) =='function' && onloading(NUM_ELEMENTS,NUM_LOADED,NUM_ERROR);
        }
    };
    function imageLoadError(){
        var imgsrc=this.getAttribute("src");
        TempProperty[imgsrc].loaded=false;
        NUM_ERROR++;
        if(NUM_LOADED+NUM_ERROR==NUM_ELEMENTS){
            typeof(onloadedcompleted) =='function' && onloadedcompleted(NUM_ELEMENTS,NUM_LOADED,NUM_ERROR);
        }else{
            typeof(onloading) =='function' && onloading(NUM_ELEMENTS,NUM_LOADED,NUM_ERROR);
        }
    };
};