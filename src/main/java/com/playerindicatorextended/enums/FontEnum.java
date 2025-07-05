package com.playerindicatorextended.enums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FontEnum {
    ARIAL("Arial"),
    CALIBRI("Calibri"),
    VERDANA("Verdana"),
    TAHOMA("Tahoma"),
    CONSOLAS("Consolas"),
    COMIC_SANS("Comic Sans MS"),
    TIMES_NEW_ROMAN("Times New Roman"),
    DIALOG("Dialog"),
    MONOSPACED("Monospaced"),
    SERIF("Serif");

    private final String id;
}