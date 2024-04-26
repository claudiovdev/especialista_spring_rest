package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.mail.internet.MimeMessage;

@Component
public class SmtpAwsEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender   ;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemaker;
    @Override
    public void enviar(Mensagem mensagem) {
        try{

            String corpo = processarTemplate(mensagem);

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo,true);
            helper.setFrom(emailProperties.getRemetente());

            mailSender.send(mimeMessage);
        }catch (Exception e){
            throw new EmailException("Não foi possivel enviar o email ", e.getCause());
        }

    }

    private String processarTemplate(Mensagem mensagem) {
        try{
            Template template = freemaker.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        }catch (Exception e){
            throw new EmailException("Não foi possivel montar o template do email ", e.getCause());
        }
    }
}
