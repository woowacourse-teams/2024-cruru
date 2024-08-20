import styled from '@emotion/styled';

const SignUpContainer = styled.form`
  width: 40rem;
  padding: 4rem;
  border-radius: 1.6rem;

  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  display: flex;
  flex-direction: column;
  gap: 3rem;
  align-items: center;
`;

const Title = styled.h1`
  ${({ theme }) => theme.typography.heading[800]}
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

const S = {
  SignUpContainer,
  Title,
  ButtonContainer,
  LinkContainer,
};

export default S;
