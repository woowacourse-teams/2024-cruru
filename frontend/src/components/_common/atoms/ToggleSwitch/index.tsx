import S, { StyleProps } from './style';

interface ToggleSwitchProps extends StyleProps {
  onChange: () => void;
}

export default function ToggleSwitch({ width, isChecked, isDisabled, onChange }: ToggleSwitchProps) {
  return (
    <S.Switch
      width={width}
      isChecked={isChecked}
      isDisabled={isDisabled}
      onClick={onChange}
    >
      <S.Knob
        isChecked={isChecked}
        isDisabled={isDisabled}
      />
    </S.Switch>
  );
}
