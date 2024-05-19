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
//    private static String oAuthToken = "xoxb-7137794229445-7153694485985-R8o6e6zctLK46CEWVf43jirl";
//    private static String slackChannel = "visualopsnotificationschannel";
//
//    public static void main(String[] args){
//        enviarMensagemParaSlack("Olá usuário! Testando a integração com Slack");
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
