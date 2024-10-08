import styled from '@emotion/styled';

const StarButton = styled.button`
  svg {
    width: 2.4rem;
    height: 2.4rem;
    color: ${({ theme }) => theme.colors.brand.primary};
  }
`;

const S = { StarButton };

export default S;
