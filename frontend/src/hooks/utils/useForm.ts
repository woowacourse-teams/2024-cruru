import { KeyedStrings } from '@customTypes/utilTypes';
import ValidationError from '@utils/errors/ValidationError';
import { ComponentPropsWithRef, FormEventHandler, useState } from 'react';

interface RegisterOptions extends ComponentPropsWithRef<'input'> {
  validate?: {
    onChange?: (value: string) => void;
    onBlur?: (value: string) => void;
  };
  formatter?: (value: string) => string;
}

interface UseFormProps<TFieldData> {
  initialValues: TFieldData;
}

export default function useForm<TFieldData>({ initialValues }: UseFormProps<TFieldData>) {
  const [formData, setFormData] = useState<TFieldData>(initialValues);
  const [errors, setErrors] = useState<KeyedStrings<TFieldData>>({} as KeyedStrings<TFieldData>);

  const validateAndSetErrors = ({
    value,
    validate,
    inputName,
  }: {
    value: string;
    validate: (data: string) => void;
    inputName: string;
  }) => {
    try {
      validate(value);
      setErrors((prev) => ({ ...prev, [inputName]: '' }));
    } catch (err) {
      if (err instanceof ValidationError) {
        setErrors((prev) => ({ ...prev, [err.inputName]: err.message }));
      }
    }
  };

  const handleSubmit: FormEventHandler<HTMLFormElement> = (e) => async (callback: () => Promise<void>) => {
    e.preventDefault();

    if (Object.values(errors).length > 0) return;

    setErrors({} as KeyedStrings<TFieldData>);
    await callback();
  };

  const register = (inputName: keyof TFieldData, options: RegisterOptions = {} as RegisterOptions) => {
    const { validate, formatter } = options;
    const eventHandlers = {} as Pick<RegisterOptions, 'onChange' | 'onBlur'>;

    eventHandlers.onChange = (e) => {
      options.onChange?.(e);

      const { name, value } = e.target;
      if (validate?.onChange) {
        validateAndSetErrors({ value, validate: validate.onChange, inputName: name });
      }

      setFormData((prev) => ({ ...prev, [name]: formatter ? formatter(value) : value }));
    };

    eventHandlers.onBlur = (e) => {
      options.onBlur?.(e);

      const { name, value } = e.target;
      if (validate?.onBlur) {
        validateAndSetErrors({ value, validate: validate.onBlur, inputName: name });
      }
    };

    return {
      ...options,
      name: inputName,
      value: formData[inputName],
      error: errors[inputName],
      onChange: eventHandlers.onChange,
      onBlur: eventHandlers.onBlur,
    };
  };

  return { register, formData, handleSubmit, validateAndSetErrors };
}
