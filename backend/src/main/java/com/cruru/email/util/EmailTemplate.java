package com.cruru.email.util;

public class EmailTemplate {

    public static String generateVerificationEmailContent(String verificationCode) {
        return """
               <div style='font-family: Arial, sans-serif; padding: 20px; background-color: #ffffff; border: 1px solid #e0e0e0; max-width: 600px; margin: 0 auto;'>
                   <div style='text-align: center; padding: 20px 0; background-color: #AA2298; color: white;'>
                       <h1 style='margin: 0;'>크루루</h1>
                   </div>
                   <div style='padding: 30px; text-align: center;'>
                       <h2 style='color: #333;'>[크루루] 인증 코드 안내</h2>
                       <p style='font-size: 16px; color: #555;'>안녕하세요,</p>
                       <p style='font-size: 16px; color: #555;'>아래 인증 코드를 입력해 주세요:</p>
                       <div style='padding: 20px; background-color: #f0f0f0; border-radius: 10px; display: inline-block; margin: 20px 0;'>
                           <span id='verificationCode' style='font-size: 32px; font-weight: bold; color: #333;'>%s</span>
                       </div>
                       <p style='font-size: 14px; color: #888;'>이 코드는 10분 후에 만료됩니다.</p>
                   </div>
                   <hr style='border: none; border-top: 1px solid #ddd; margin: 20px 0;'>
                   <div style='padding: 20px; text-align: center; font-size: 12px; color: #888;'>
                       <p>본 메일은 크루루 시스템에 의해 자동 발송되었습니다.</p>
                       <p>&copy; 2024 크루루. All Rights Reserved.</p>
                   </div>
               </div>
               """.formatted(verificationCode);
    }

    public static String generateDefaultEmailTemplate(String sender, String title, String content) {
        return """
            <div style='font-family: Arial, sans-serif; padding: 20px; background-color: #ffffff; border: 1px solid #e0e0e0; max-width: 1000px; margin: 0 auto;'>
                    <div style='text-align: center; padding: 20px 0; border-top: 5px solid #AA2298; border-bottom: 5px solid #AA2298; color: Black;'>
                        <h1 style='margin: 0;'>%s</h1>
                    </div>
                    <div style='padding: 30px;'>
                        <h2 style='color: #333;'>%s</h2>
                        <p style='font-size: 16px; color: #555;'>%s</p>
                    </div>
                    <hr style='border: none; border-top: 1px solid #ddd; margin: 20px 0;'>
                    <div style='padding: 20px; text-align: center; font-size: 12px; color: #888;'>
                        <p>본 메일은 수신 전용으로 답장이 불가하며, <a href="https://www.cruru.kr" style="color: #AA2298; text-decoration: none;">크루루(Cruru)</a>를 통해 발송되었습니다.</p>
                        <p>복잡했던 리크루팅 하루만에 크루루!</p>
                        <p>&copy; 2024 크루루. All Rights Reserved.</p>
                    </div>
                </div>
            """.formatted(sender, title, content.replace(System.lineSeparator(), "<br/>"));
    }

    public static String defaultEmailSubject(String sender, String title) {
        return "[%s] %s".formatted(sender, title);
    }
}
