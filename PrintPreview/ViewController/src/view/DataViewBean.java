package view;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class DataViewBean {
    
    private List<DataModel> dataList;
    
    public DataViewBean() {
        super();
        dataList = new ArrayList<DataModel>();
        for (int i = 0; i < 100; i++) {
            DataModel data = new DataModel();
            data.setCol1(String.format("hoge%03d", i+1));
            data.setCol2(String.format("fuga%03d", i+1));
            data.setCol3(String.format("piyo%03d", i+1));
            data.setCol4(String.format("kiya%03d", i+1));
            this.dataList.add(data);
        }
    }   

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public List getDataList() {
        return dataList;
    }
    
    public void beforePhaseMethod(PhaseEvent phaseEvent) {
        
        System.out.println("----------   phaseEvent   -------------");
        System.out.println("phaseEvent.getPhaseId():" + phaseEvent.getPhaseId());
        
        //only perform action if RENDER_RESPONSE phase is reached
        if (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            
            FacesContext fctx = FacesContext.getCurrentInstance();
            //check internal request parameter
            Map requestMap = fctx.getExternalContext().getRequestMap();
            
            // リクエストコンテキストに印刷らしきキーを特定
            Object showPrintableBehavior = requestMap.get("oracle.adfinternal.view.faces.el.PrintablePage");
            if (showPrintableBehavior != null) {
                // 印刷画面なら、表示するDOM要素を整理する
                if (Boolean.TRUE == showPrintableBehavior) {
                    UiComponentControlUtil.setUnRendered("mb1", "it1", "it2");
                    UiComponentControlUtil.callbackJavascript("customPrint();");
                }
            }
        }
    }
}
