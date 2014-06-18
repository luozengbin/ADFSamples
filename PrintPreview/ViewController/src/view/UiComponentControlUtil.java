package view;

import java.util.Iterator;

import java.util.List;
import java.util.Locale;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


/**
 * UiComponentを制御します。
 *
 * <pre>
 * UiComponentを制御する共通メソッドを定義します。
 * </pre>
 *
 */
public class UiComponentControlUtil {

  /** ユーティリティクラス、インスタンス化させない */
  private UiComponentControlUtil() {
  }

  /**
   * 対象コンポーネントをリフレッシュ（部分ページ・レンダリング）します。
   * @param id リフレッシュ対象コンポーネントのID
   */
  public static void refreshComponent(String id) {
    refreshComponent(UiComponentControlUtil.findComponentInRoot(id));
  }

  /**
   * 対象コンポーネントをリフレッシュ（部分ページ・レンダリング）します。
   * @param component リフレッシュ対象コンポーネント
   */
  public static void refreshComponent(UIComponent component) {
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(component);
  }

  /**
   * ルートドキュメントTreeを対象に、指定コンポーネントを検索します。
   * @param id 対象コンポーネントのID
   *
   * @return 該当コンポーネント
   */
  public static UIComponent findComponentInRoot(String id) {
    UIComponent component = null;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    if (facesContext != null) {
      UIComponent root = facesContext.getViewRoot();
      component = findComponent(root, id);
    }
    return component;
  }

  /**
   * カレントドキュメントTreeを対象に、指定コンポーネントを検索します。
   * @param base カレントドキュメント
   * @param id 対象コンポーネントのID
   * @return 該当コンポーネント
   */
  public static UIComponent findComponent(UIComponent base, String id) {
    if (id.equals(base.getId())) {
      return base;
    }

    UIComponent children = null;
    UIComponent result = null;
    Iterator childrens = base.getFacetsAndChildren();
    while (childrens.hasNext() && (result == null)) {
      children = (UIComponent) childrens.next();
      if (id.equals(children.getId())) {
        result = children;
        break;
      }
      result = findComponent(children, id);
      if (result != null) {
        break;
      }
    }
    return result;
  }

  /**
   * ユーザロケールを取得します。
   * @return Locale ユーザロケール
   */
  public static Locale getUserLocale() {
    return FacesContext.getCurrentInstance().getViewRoot().getLocale();
  }
  
  /**
   * レスポンスにjavascriptを注入します。
   * @param javascript javascriptソースコード
   */
  public static void callbackJavascript(String javascript) {
    FacesContext context = FacesContext.getCurrentInstance();
    ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
    erks.addScript(context, javascript);
  }

  /**
   * ページリフレッシュ
   */
  public static void refreshPage() {
    FacesContext fctx = FacesContext.getCurrentInstance();
    String refreshpage = fctx.getViewRoot().getViewId();
    ViewHandler ViewH = fctx.getApplication().getViewHandler();
    UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
    UIV.setViewId(refreshpage);
    fctx.setViewRoot(UIV);
  }

    /**
     * コンポーネントを非表示に設定する。
     * @param unRenderComponentIds 非表示コンポーネントIDリスト
     */
    public static void setUnRendered(String ... unRenderComponentIds) {
        for (String compId : unRenderComponentIds) {
            UIComponent uicomp = findComponentInRoot(compId);
            if(uicomp != null) {
                uicomp.setRendered(false);
            }
        }
    }
}
