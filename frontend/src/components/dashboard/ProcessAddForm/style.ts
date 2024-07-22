import styled from '@emotion/styled';

const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 0.8rem;

  padding: 2.4rem 1.6rem;

  border-radius: 0.8rem;
  border: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[300]};
  background-color: ${({ theme }) => theme.baseColors.grayscale[100]};
`;

const Label = styled.label`
  margin-top: 0.8rem;

  font-size: 1.4rem;
  font-weight: 700;
`;

const Input = styled.input`
  font-size: 1.4rem;
  color: ${({ theme }) => theme.baseColors.grayscale[900]};
  padding: 0.8rem;

  &:focus {
    outline: none;
  }
`;

const FormButton = styled.button<{ color: 'primary' | 'secondary' }>`
  border: none;
  border-radius: 0.8rem;

  width: 100%;
  padding: 1rem;

  color: ${({ color, theme }) => {
    if (color === 'primary') return theme.baseColors.grayscale[50];
    return theme.baseColors.grayscale[900];
  }};
  background-color: ${({ color, theme }) => {
    if (color === 'primary') return theme.baseColors.purplescale[700];
    return theme.baseColors.grayscale[300];
  }};
`;

const S = {
  Form,
  Label,
  Input,
  FormButton,
};

export default S;
