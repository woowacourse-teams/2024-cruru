import styled from '@emotion/styled';

const SignInContainer = styled.div`
  width: 40rem;
  padding: 4rem;
  border-radius: 1.6rem;

  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #ffffff;

  display: flex;
  flex-direction: column;
  gap: 3rem;
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
  top: 4.2rem;
  right: 1.8rem;

  * {
    font-size: 2rem;
    color: ${({ theme }) => theme.baseColors.grayscale[700]};
    cursor: pointer;
  }
`;

const S = {
  SignInContainer,
  Title,
  Description,
  ButtonContainer,
  LinkContainer,

  PasswordInputContainer,
  IconButton,
};

export default S;
