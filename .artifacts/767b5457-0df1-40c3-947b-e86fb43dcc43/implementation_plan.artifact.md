# Implementation Plan - Add Quantity Controls (+/-) to Products

This plan focuses on adding quantity increment/decrement controls to products, specifically in the Cart Sidebar, and ensuring the ViewModel can handle these updates.

## Proposed Changes

### 1. [viewmodel] [ShopViewModel.kt](file:///C:/Android2026/ANDROIDS26/AndroidDayTwo/app/src/main/java/com/example/androiddaytwo/viewmodel/ShopViewModel.kt)
- Add `updateCartItemQuantity(cartItem: CartItem, newQuantity: Int)` method.
- This will allow the UI to update the quantity of items already in the cart.

### 2. [ui] [Components.kt](file:///C:/Android2026/ANDROIDS26/AndroidDayTwo/app/src/main/java/com/example/androiddaytwo/ui/Components.kt)
- **Refactor `QuantitySelector`**: Ensure it's reusable and potentially create a smaller version for the sidebar.
- **Update `CartSidebarItem`**:
    - Replace the static "Qty: X" text with the `QuantitySelector` (or a compact version of it).
    - Connect the increment/decrement actions to the new ViewModel method.
- **Update `CartSidebarContent`**:
    - Pass the `onUpdateQuantity` callback down to `CartSidebarItem`.

### 3. [ui] [MainActivity.kt](file:///C:/Android2026/ANDROIDS26/AndroidDayTwo/app/src/main/java/com/example/androiddaytwo/MainActivity.kt)
- Update the `CartSidebarContent` usage to handle the `onUpdateQuantity` event.

## Verification Plan

### Manual Verification
1. **Add to Cart**: Add a product to the cart from the details screen.
2. **Open Sidebar**: Click the cart icon to open the sidebar.
3. **Test Controls**:
    - Click the "+" button in the sidebar for the added product. Verify the quantity increases and the total price updates.
    - Click the "-" button. Verify the quantity decreases.
    - Verify that decreasing below 1 either stops at 1 or removes the item (usually stops at 1, or follows UX preference).
4. **Consistency**: Ensure the cart total in the sidebar correctly reflects the updated quantities.
