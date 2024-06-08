package app.integration;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import io.github.cdimascio.dotenv.Dotenv;

public class SlackIntegration {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String webHooksUrl = dotenv.get("SLACK_WEBHOOK_URL");
    private static final String oAuthToken = dotenv.get("SLACK_OAUTH_TOKEN");
    private static final String slackChannel = dotenv.get("SLACK_CHANNEL");

    public static void main(String[] args) {
        enviarMensagemParaSlack("Olá usuário! Testando a integração com Slack 🙂 ");
    }

    public static void enviarMensagemParaSlack(String mensagem) {
        try {
            StringBuilder msgBuilder = new StringBuilder();
            msgBuilder.append(mensagem);

            Payload payload = Payload.builder()
                    .channel(slackChannel)
                    .text(msgBuilder.toString())
                    .build();

            WebhookResponse wbResp = Slack.getInstance().send(webHooksUrl, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

