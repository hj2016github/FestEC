package com.gehj.general_core.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * Created by 傅令杰
 * 结合tab的实体类与fragment的中间的类;
 * 最主要就是添加items;
 */

public final class ItemBuilder {
/*1,建立Bean与Delegate的映射关系 2,linkHashMap为有序的map,保证底下tab不错乱*/
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();//

    static ItemBuilder builder() {//简单工厂;
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bean, BottomItemDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }
}
