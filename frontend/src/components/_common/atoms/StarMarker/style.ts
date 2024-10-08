import styled from '@emotion/styled';

const StarButton = styled.button`
  svg {
    width: 2rem;
    height: 2rem;
    color: ${({ theme }) => theme.colors.brand.primary};
  }
`;

const S = { StarButton };

export default S;
