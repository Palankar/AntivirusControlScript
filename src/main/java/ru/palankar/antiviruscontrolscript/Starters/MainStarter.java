package ru.palankar.antiviruscontrolscript.Starters;

import ru.palankar.antiviruscontrolscript.ScriptBody;

public class MainStarter {
    private static ScriptBody scriptBody;

    public static void main(String[] args) {
        scriptBody = new ScriptBody();
        scriptBody.startScript();
    }
}
