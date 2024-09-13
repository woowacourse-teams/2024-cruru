import { PropsWithChildren } from 'react';
import S, { IconButtonStyleProps } from './style';

type IconButtonProps = IconButtonStyleProps & React.ComponentProps<'button'>;

export default function IconButton({
  size = 'sm',
  outline = true,
  shape = 'round',
  type = 'button',
  disabled,
  onClick,
  children,
  ...props
}: PropsWithChildren<IconButtonProps>) {
  return (
    <S.IconButton
      size={size}
      outline={outline}
      shape={shape}
      type={type}
      disabled={disabled}
      onClick={onClick}
      {...props}
    >
      {children}
    </S.IconButton>
  );
}
