package com.gehj.fastec.generators;

import com.gehj.general_annotation.EntryGenerator;
import com.gehj.general_core.wechat.templates.WXEntryTemplate;

/**
 * Created by 傅令杰 on 2017/4/22
 */

@SuppressWarnings("unused")
@EntryGenerator(
        packageName = "com.gehj.fastec",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
