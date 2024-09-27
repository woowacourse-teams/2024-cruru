import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const bounceInUp = keyframes`
  0% {
    opacity: 0;
    transform: translateY(20px);
  }
  100% {
    transform: translateY(0);
  }
`;

const Layout = styled.div`
  width: 100vw;
  height: 100vh;

  display: flex;
  justify-content: center;
  align-items: center;
`;

const SignInContainer = styled.form`
  width: 40rem;
  padding: 4rem;
  border-radius: 1.6rem;

  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  display: flex;
  flex-direction: column;
  gap: 3rem;

  animation: ${bounceInUp} 1s ease forwards;
`;

const Title = styled.h1`
  ${({ theme }) => theme.typography.heading[800]}
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const Description = styled.p`
  ${({ theme }) => theme.typography.common.paragraph}
`;

const ButtonContainer = styled.div`
  width: 100%;
  height: 4.6rem;
`;

const LinkContainer = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  gap: 1.4rem;

  a {
    ${({ theme }) => theme.typography.common.block}
    color: ${({ theme }) => theme.baseColors.grayscale[700]};
    margin-bottom: 0;

    &:hover {
      text-decoration: underline;
    }
  }
`;

const PasswordInputContainer = styled.div`
  position: relative;
  width: 100%;
`;

const IconButton = styled.div`
  position: absolute;
  top: 4.1rem;
  right: 1.8rem;

  * {
    font-size: 2rem;
    color: ${({ theme }) => theme.baseColors.grayscale[700]};
    cursor: pointer;
  }
`;

const MsgContainer = styled.div`
  position: absolute;
  display: flex;
  flex-direction: column;
  padding: 0.8rem;
  gap: 0.2rem;

  margin-top: 0.4rem;
  width: 100%;
  border: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 0.8rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const MsgWrapper = styled.div`
  display: flex;
  gap: 0.4rem;

  ${({ theme }) => theme.typography.common.smallAccent}
`;

const CheckIcon = styled.div<{ isValid: boolean }>`
  font-size: 1.4rem;
  color: ${({ theme, isValid }) => (isValid ? theme.colors.feedback.success : theme.colors.feedback.error)};
`;

const Message = styled.div<{ isValid: boolean }>`
  ${({ theme }) => theme.typography.common.small};
  color: ${({ theme, isValid }) => (isValid ? theme.colors.feedback.success : theme.baseColors.grayscale[700])};
`;

const S = {
  Layout,
  SignInContainer,
  Title,
  Description,
  ButtonContainer,
  LinkContainer,

  PasswordInputContainer,
  MsgContainer,
  MsgWrapper,
  CheckIcon,
  Message,
  IconButton,
};

export default S;
