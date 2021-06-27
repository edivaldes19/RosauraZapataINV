package com.manuel.rosaurazapatainv.CallBacks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.rosaurazapatainv.Adaptadores.AdapterItemTecnicoPedagogico;
import com.manuel.rosaurazapatainv.Interfaces.CallBackItemTouch;
import com.manuel.rosaurazapatainv.R;

import org.jetbrains.annotations.NotNull;

public class MyItemTouchHelperCallbackTP extends ItemTouchHelper.Callback {
    CallBackItemTouch callBackItemTouch;

    public MyItemTouchHelperCallbackTP(CallBackItemTouch callBackItemTouch) {
        this.callBackItemTouch = callBackItemTouch;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull @org.jetbrains.annotations.NotNull RecyclerView recyclerView, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull @org.jetbrains.annotations.NotNull RecyclerView recyclerView, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder target) {
        callBackItemTouch.itemTouchOnMode(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, int direction) {
        callBackItemTouch.onSwiped(viewHolder, viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        } else {
            final View foreGroundView = ((AdapterItemTecnicoPedagogico.ViewHolderTecnicoPedagogico) viewHolder).viewB;
            getDefaultUIUtil().onDrawOver(c, recyclerView, foreGroundView, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState != ItemTouchHelper.ACTION_STATE_DRAG) {
            final View foreGroundView = ((AdapterItemTecnicoPedagogico.ViewHolderTecnicoPedagogico) viewHolder).viewF;
            getDefaultUIUtil().onDraw(c, recyclerView, foreGroundView, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
        final View foreGroundView = ((AdapterItemTecnicoPedagogico.ViewHolderTecnicoPedagogico) viewHolder).viewF;
        foreGroundView.setBackgroundColor(ContextCompat.getColor(((AdapterItemTecnicoPedagogico.ViewHolderTecnicoPedagogico) viewHolder).viewF.getContext(), R.color.white));
        getDefaultUIUtil().clearView(foreGroundView);
    }

    @Override
    public void onSelectedChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (viewHolder != null) {
            final View foreGroundView = ((AdapterItemTecnicoPedagogico.ViewHolderTecnicoPedagogico) viewHolder).viewF;
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                foreGroundView.setBackgroundColor(Color.LTGRAY);
            }
            getDefaultUIUtil().onSelected(foreGroundView);
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}