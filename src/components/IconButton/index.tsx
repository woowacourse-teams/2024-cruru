import { PropsWithChildren } from 'react';
import Button from '../Button';
import S, { IconButtonStyleProps } from './style';

type IconButtonProps = IconButtonStyleProps & React.ComponentProps<'button'>;

export default function IconButton({
  type,
  onClick,

  children,
  size,
  outline = true,
  borderRadius = '0.8rem',
}: PropsWithChildren<IconButtonProps>) {
  return (
    <Button
      type={type}
      onClick={onClick}
    >
      <S.IconWrapper
        size={size}
        outline={outline}
        borderRadius={borderRadius}
      >
        {children}
      </S.IconWrapper>
    </Button>
  );
}
