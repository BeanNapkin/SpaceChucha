package pro.fateeva.spacechucha.view.notes

interface ItemTouchCallback {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}