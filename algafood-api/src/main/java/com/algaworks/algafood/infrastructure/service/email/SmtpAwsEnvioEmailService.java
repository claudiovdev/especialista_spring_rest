package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class SmtpAwsEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender   ;

    @Autowired
    private EmailProperties emailProperties;
    @Override
    public void enviar(Mensagem mensagem) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(mensagem.getCorpo(),true);
            helper.setFrom(emailProperties.getRemetente());

            mailSender.send(mimeMessage);
        }catch (Exception e){
            throw new EmailException("Não foi possivel enviar o email ", e.getCause());
        }

    }
}
