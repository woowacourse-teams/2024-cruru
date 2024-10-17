import { forwardRef, PropsWithChildren, Ref } from 'react';
import S, { IconButtonStyleProps } from './style';

type IconButtonProps = IconButtonStyleProps & React.ComponentProps<'button'>;

const IconButton = forwardRef(
  (
    {
      size = 'sm',
      outline = true,
      shape = 'round',
      type = 'button',
      disabled,
      onClick,
      children,
      ...props
    }: PropsWithChildren<IconButtonProps>,
    ref: Ref<HTMLButtonElement>,
  ) => (
    <S.IconButton
      ref={ref}
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
  ),
);

export default IconButton;
