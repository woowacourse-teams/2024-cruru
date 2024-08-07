import styled from '@emotion/styled';
import { hiddenStyles } from '@styles/utils';

const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 0.8rem;
  padding: 0.8rem 2rem;
  width: 100%;

  position: relative;
`;

const Wrapper = styled.div`
  display: flex;
`;

const Label = styled.span`
  ${({ theme }) => theme.typography.common.default};
  color: ${({ theme }) => theme.baseColors.grayscale[600]};
  margin-right: 0.5rem;
`;

const Text = styled.span`
  min-width: 10rem;
  ${({ theme }) => theme.typography.common.default};
`;

const Input = styled.input`
  ${hiddenStyles}
  position: absolute;
  top: 3rem;
  right: 3rem;
`;

const Icon = styled.div`
  color: ${({ theme }) => theme.baseColors.grayscale[600]};
  cursor: pointer;
  padding: 0.2rem;
`;

const S = {
  Container,
  Wrapper,
  Label,
  Text,
  Input,
  Icon,
};

export default S;
