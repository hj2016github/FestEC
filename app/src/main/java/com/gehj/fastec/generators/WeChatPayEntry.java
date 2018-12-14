package com.gehj.fastec.generators;


import com.gehj.general_annotation.PayEntryGenerator;
import com.gehj.general_core.wechat.templates.WXPayEntryTemplate;

/**
 * Created by 傅令杰 on 2017/4/22
 */
@SuppressWarnings("unused")
@PayEntryGenerator(
        packageName = "com.gehj.fastec",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
