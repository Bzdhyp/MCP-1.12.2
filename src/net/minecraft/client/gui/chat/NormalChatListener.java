package net.minecraft.client.gui.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class NormalChatListener implements IChatListener {
    private final Minecraft mc;

    public NormalChatListener(Minecraft minecraftIn)
    {
        this.mc = minecraftIn;
    }

    /**
     * Called whenever this listener receives a chat message, if this listener is registered to the given type in
     *
     * @param chatTypeIn The type of chat message
     * @param message The chat message.
     */
    public void say(ChatType chatTypeIn, ITextComponent message) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(message);
    }
}
