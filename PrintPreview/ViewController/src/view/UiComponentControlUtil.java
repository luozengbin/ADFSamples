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
 * UiComponent�𐧌䂵�܂��B
 *
 * <pre>
 * UiComponent�𐧌䂷�鋤�ʃ��\�b�h���`���܂��B
 * </pre>
 *
 */
public class UiComponentControlUtil {

  /** ���[�e�B���e�B�N���X�A�C���X�^���X�������Ȃ� */
  private UiComponentControlUtil() {
  }

  /**
   * �ΏۃR���|�[�l���g�����t���b�V���i�����y�[�W�E�����_�����O�j���܂��B
   * @param id ���t���b�V���ΏۃR���|�[�l���g��ID
   */
  public static void refreshComponent(String id) {
    refreshComponent(UiComponentControlUtil.findComponentInRoot(id));
  }

  /**
   * �ΏۃR���|�[�l���g�����t���b�V���i�����y�[�W�E�����_�����O�j���܂��B
   * @param component ���t���b�V���ΏۃR���|�[�l���g
   */
  public static void refreshComponent(UIComponent component) {
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(component);
  }

  /**
   * ���[�g�h�L�������gTree��ΏۂɁA�w��R���|�[�l���g���������܂��B
   * @param id �ΏۃR���|�[�l���g��ID
   *
   * @return �Y���R���|�[�l���g
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
   * �J�����g�h�L�������gTree��ΏۂɁA�w��R���|�[�l���g���������܂��B
   * @param base �J�����g�h�L�������g
   * @param id �ΏۃR���|�[�l���g��ID
   * @return �Y���R���|�[�l���g
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
   * ���[�U���P�[�����擾���܂��B
   * @return Locale ���[�U���P�[��
   */
  public static Locale getUserLocale() {
    return FacesContext.getCurrentInstance().getViewRoot().getLocale();
  }
  
  /**
   * ���X�|���X��javascript�𒍓����܂��B
   * @param javascript javascript�\�[�X�R�[�h
   */
  public static void callbackJavascript(String javascript) {
    FacesContext context = FacesContext.getCurrentInstance();
    ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
    erks.addScript(context, javascript);
  }

  /**
   * �y�[�W���t���b�V��
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
     * �R���|�[�l���g���\���ɐݒ肷��B
     * @param unRenderComponentIds ��\���R���|�[�l���gID���X�g
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
