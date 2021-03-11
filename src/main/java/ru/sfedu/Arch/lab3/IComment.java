package ru.sfedu.Arch.lab3;

import ru.sfedu.Arch.Enums;

import java.util.UUID;

public interface IComment {
    public String getText();
    public void setText(String text);
    public String getDatetime ();
    public void setDatetime (String datetime);
    public void setRole (Enums.Role role);
    public void setPresentationId(UUID id);
}