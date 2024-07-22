import styled from '@emotion/styled';

const heightStyle = {
  sm: '1.2rem',
  md: '2.0rem',
  lg: '3.2rem',
};

const Image = styled.img<{ size: 'sm' | 'md' | 'lg' }>`
  height: ${({ size }) => heightStyle[size]};
  aspect-ratio: 1.2;
`;
const S = {
  Image,
};

export default S;
