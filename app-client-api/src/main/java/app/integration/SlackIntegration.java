//package app.integration;
//
//import com.github.seratch.jslack.Slack;
//import com.github.seratch.jslack.api.webhook.Payload;
//import com.github.seratch.jslack.api.webhook.WebhookResponse;
//
//public class SlackIntegration {
//
//    private static String webHooksUrl =
//            "https://hooks.slack.com/services/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";
//    private static String oAuthToken = "xoxb-7137794229445-7163850523392-3dUSE3k7neqNqn7iub8Lbaou";
//    private static String slackChannel = "visualopsnotificationschannel";
//
//    public static void main(String[] args){
//        enviarMensagemParaSlack("Olá usuário! Testando a integração com Slack denovoo");
//    }
//
//    public static void enviarMensagemParaSlack(String mensagem){
//        try {
//            StringBuilder msgBuilder = new StringBuilder();
//            msgBuilder.append(mensagem);
//
//            Payload payload = Payload.builder().channel(slackChannel).text(msgBuilder.toString()).build();
//
//            WebhookResponse wbResp = Slack.getInstance().send( webHooksUrl,payload);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//}
