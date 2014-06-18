function customPrint() {

  if (getBrowser().indexOf('ie8') !== -1 ) {
    if(window.ActiveXObject == null || document.body.insertAdjacentHTML == null) {
      return;
    }
    var sWebBrowserCode = '<object width="0" height="0" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></object>';
    document.body.insertAdjacentHTML('beforeEnd', sWebBrowserCode);
    var objWebBrowser = document.body.lastChild;
    if(objWebBrowser == null) return;
    objWebBrowser.ExecWB(7, 1);
    document.body.removeChild(objWebBrowser);
  } else {
    window.print();
  }
  
  // ����v���r���[��ʕ��遨HTML��ʂ����
  window.onfocus = function(){
   (window.open('','_self').opener=window).close(); 
  };
}


/**
 *  �u���E�U�����擾
 *  
 *  @return     �u���E�U��(ie6�Aie7�Aie8�Aie9�Aie10�Aie11�Achrome�Asafari�Aopera�Afirefox�Aunknown)
 *
 */
var getBrowser = function(){
    var ua = window.navigator.userAgent.toLowerCase();
    var ver = window.navigator.appVersion.toLowerCase();
    var name = 'unknown';

    if (ua.indexOf("msie") != -1){
        if (ver.indexOf("msie 6.") != -1){
            name = 'ie6';
        }else if (ver.indexOf("msie 7.") != -1){
            name = 'ie7';
        }else if (ver.indexOf("msie 8.") != -1){
            name = 'ie8';
        }else if (ver.indexOf("msie 9.") != -1){
            name = 'ie9';
        }else if (ver.indexOf("msie 10.") != -1){
            name = 'ie10';
        }else{
            name = 'ie';
        }
    }else if(ua.indexOf('trident/7') != -1){
        name = 'ie11';
    }else if (ua.indexOf('chrome') != -1){
        name = 'chrome';
    }else if (ua.indexOf('safari') != -1){
        name = 'safari';
    }else if (ua.indexOf('opera') != -1){
        name = 'opera';
    }else if (ua.indexOf('firefox') != -1){
        name = 'firefox';
    }
    return name;
};
