package lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.UUID;

public class EmailHandler implements RequestHandler<SNSEvent, Object> {

    public Object handleRequest(SNSEvent request, Context context) {

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        context.getLogger().log("Lambda started: " + timeStamp);
        String domain = System.getenv("domain");
        context.getLogger().log("Domain : " + domain);
        final String FROM = "CSYE6225@" + domain;
        String TO = request.getRecords().get(0).getSNS().getMessage();
        context.getLogger().log("Email : " + TO);

        AmazonDynamoDB dbclient = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDb = new DynamoDB(dbclient);
        Table table = dynamoDb.getTable("csye6225");

        long addedTime = Instant.now().getEpochSecond() + 60 * 15;
        long now = Instant.now().getEpochSecond();

        Item item = table.getItem("id",TO);
        if (item == null || (item != null && Long.parseLong(item.get("expiration").toString()) < now)) {
            String token = UUID.randomUUID().toString();
            Item itemPut = new Item()
                    .withPrimaryKey("id", TO)
                    .withString("token", token)
                    .withNumber("expiration", addedTime);
            table.putItem(itemPut);

            String htmlBody = "Click on the following link to reset your password <br /> "
                    + "<a href = https://"+ domain + "/pwdreset?email=" + TO + "&token=" + token +">" +"https://"+ domain + "/pwdreset?email=" + TO + "&token=" + token + "</a>";

            AmazonSimpleEmailService emailClient = AmazonSimpleEmailServiceClientBuilder.defaultClient();
            SendEmailRequest emailRequest = new SendEmailRequest().withDestination(new Destination().withToAddresses(TO))
                    .withMessage(new Message()
                            .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBody)))
                            .withSubject(new Content().withCharset("UTF-8").withData("Password Reset Link")))
                    .withSource(FROM);
            emailClient.sendEmail(emailRequest);
            context.getLogger().log("Email sent");
        }
        return null;
    }
}
