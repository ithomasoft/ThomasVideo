package com.thomas.video.core;


import com.thomas.base.mvp.BaseMvpPresenter;
import com.thomas.base.mvp.IBaseMvpView;

/**
 * @author Thomas
 */
public abstract class AbstractMvpFragment<P extends BaseMvpPresenter> extends AbstractFragment implements IBaseMvpView {
    protected P presenter;


    @Override
    public void setContentView() {
        super.setContentView();
        //创建present
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除绑定，避免内存泄露
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }

    protected abstract P createPresenter();
}
