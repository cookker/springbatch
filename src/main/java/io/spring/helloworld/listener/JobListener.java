package io.spring.helloworld.listener;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@AllArgsConstructor
public class JobListener implements JobExecutionListener {
    private JavaMailSender mailSender;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();

        SimpleMailMessage mail = getSimpleMailMessage(String.format("%s is starting", jobName),
                String.format("Per your request, we are informing you that %s is starting", jobName));

        mailSender.send(mail);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();

        SimpleMailMessage mail = getSimpleMailMessage(String.format("%s has completed", jobName),
                String.format("Per your request, we are informing you that %s has completed", jobName));
        mailSender.send(mail);
    }

    private SimpleMailMessage getSimpleMailMessage(String subject, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("i3rewmaster@gmail.com");
        mail.setSubject(subject);
        mail.setText(text);
        return mail;
    }


}
