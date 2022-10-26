package com.example.newauthtest.bot;

import com.example.newauthtest.model.Status2FAModel;
import com.example.newauthtest.model.UserModel;
import com.example.newauthtest.service.impl.StatusServiceImpl;
import com.example.newauthtest.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AuthTelegramBot extends TelegramLongPollingBot {
    private final UserServiceImpl userService;
    private final StatusServiceImpl statusService;

    @Autowired
    public AuthTelegramBot(UserServiceImpl userService, StatusServiceImpl statusService) {
        this.statusService = statusService;
        this.userService = userService;
    }

    private @Value("${bot.username}") String botUsername;
    private @Value("${bot.token}") String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String status = update.getCallbackQuery().getData();

            Long userCharId = update.getCallbackQuery().getFrom().getId();
            UserModel userModel = userService.findByChatId(userCharId);

            if (userModel != null) {
                Status2FAModel status2FAModel = new Status2FAModel();

                status2FAModel.setUserAnswer2FA(status);
                status2FAModel.setUserChatId(userCharId);

                statusService.save(status2FAModel);
            }

            log.info(userCharId.toString());
            log.info(status);
        }

        if (update.hasMessage()) {
            log.info(update.getMessage().getText());
            log.info(update.getMessage().getChatId().toString());

            SendMessage sendMessage = new SendMessage();

            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText("Hello");

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMessage(SendMessage sendMessage) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> buttonRow1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow2 = new ArrayList<>();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton();
        buttonYes.setText("Yes, it was me");
        buttonYes.setCallbackData("true");

        InlineKeyboardButton buttonNo = new InlineKeyboardButton();
        buttonNo.setText("No, it wasn't me");
        buttonNo.setCallbackData("false");

        buttonRow1.add(buttonYes);
        buttonRow2.add(buttonNo);

        buttons.add(buttonRow1);
        buttons.add(buttonRow2);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(buttons);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }
}